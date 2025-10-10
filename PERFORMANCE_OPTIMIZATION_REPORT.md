# Performance Optimization Report

## Executive Summary

This report documents performance inefficiencies identified in the case-test banking API codebase during a comprehensive code review. The issues are categorized by priority based on their performance impact and frequency of execution.

**Total Issues Identified:** 8
- High Priority: 2
- Medium Priority: 3  
- Low Priority: 3

---

## High Priority Issues

### 1. Double Iteration in Ledger.calculateBalance()

**Location:** `account/src/main/java/com/casetest/account/domain/Ledger.java:35-47`

**Description:**
The `calculateBalance()` method iterates through the activities list twice - once to calculate debit and once to calculate credit. This results in O(2n) time complexity when O(n) is achievable.

**Current Implementation:**
```java
public Money calculateBalance(Account.AccountId accountId) {
    Money debit = activities.stream()
            .filter(a -> a.getTargetAccountId().equals(accountId))
            .map(Activity::getMoney)
            .reduce(Money.ZERO, Money::add);

    Money credit = activities.stream()
            .filter(a -> a.getSourceAccountId().equals(accountId))
            .map(Activity::getMoney)
            .reduce(Money.ZERO, Money::add);

    return Money.add(debit, credit.negate());
}
```

**Performance Impact:** 
- Called on every transaction during `mayCredit()` check
- Critical path operation - affects all transfer operations
- For accounts with many activities, doubles the processing time

**Suggested Fix:**
Combine both calculations into a single stream iteration using reduce with an accumulator array.

**Priority:** HIGH - This is in the critical transaction path

**Status:** ✅ FIXED in this PR

---

### 2. Incorrect Comparison Logic in Money.isGreaterThan()

**Location:** `account/src/main/java/com/casetest/account/domain/Money.java:35-37`

**Description:**
The `isGreaterThan()` method uses `>= 1` instead of `> 0` for comparison, which is incorrect for BigDecimal comparisons.

**Current Implementation:**
```java
public boolean isGreaterThan(Money money){
    return this.amount.compareTo(money.amount) >= 1;
}
```

**Issue:**
`compareTo()` returns -1, 0, or 1, but the condition `>= 1` only catches exactly equal or greater values. This should be `> 0` to properly handle all cases where the first value is greater.

**Performance Impact:**
- Can cause incorrect behavior in limit checks
- May allow transfers that should be blocked or block valid transfers

**Suggested Fix:**
```java
public boolean isGreaterThan(Money money){
    return this.amount.compareTo(money.amount) > 0;
}
```

**Priority:** HIGH - Correctness issue that affects business logic

---

## Medium Priority Issues

### 3. Repeated Stream Iteration in checkIfExeccededLimitPerDay()

**Location:** `account/src/main/java/com/casetest/account/application/service/TransactionService.java:90-100`

**Description:**
The method streams through all activities on every transaction to calculate the daily transferred amount. This is inefficient for accounts with many activities.

**Current Implementation:**
```java
private void checkIfExeccededLimitPerDay(Account account, Money money) {
    LocalDateTime limitPerDay = LocalDateTime.now().minusHours(24);
    Money transferedToday = account.getLedger().getActivities().stream()
        .filter(a -> a.getWhenHappened().isAfter(limitPerDay))
        .map(Activity::getMoney)
        .reduce(Money.ZERO, Money::add);
    // ... rest of method
}
```

**Performance Impact:**
- Executed on every successful transaction
- O(n) where n is total activities (not just last 24h)
- Could be optimized with caching or maintaining a running total

**Suggested Fix:**
- Option 1: Cache the daily total and update incrementally
- Option 2: Query the database with a date filter instead of filtering in memory
- Option 3: Maintain a separate field for today's total

**Priority:** MEDIUM - Frequent operation but typically small activity lists

---

### 4. Separate Min/Max Iterations in Ledger Timestamp Methods

**Location:** `account/src/main/java/com/casetest/account/domain/Ledger.java:13-25`

**Description:**
The `getStartTimestamp()` and `getEndTimestamp()` methods each iterate through all activities separately to find min and max timestamps.

**Current Implementation:**
```java
public LocalDateTime getStartTimestamp() {
    return activities.stream()
            .min(Comparator.comparing(Activity::getWhenHappened))
            .orElseThrow(IllegalStateException::new)
            .getWhenHappened();
}

public LocalDateTime getEndTimestamp() {
    return activities.stream()
            .max(Comparator.comparing(Activity::getWhenHappened))
            .orElseThrow(IllegalStateException::new)
            .getWhenHappened();
}
```

**Performance Impact:**
- If both methods are called together, iterates list twice
- O(2n) when O(n) is possible

**Suggested Fix:**
- Calculate both min and max in a single pass using a custom accumulator
- Or cache the values and update on activity addition
- Or maintain sorted activities list

**Priority:** MEDIUM - Less frequently called than calculateBalance

---

### 5. Repeated Gson Instance Creation

**Location:** `account/src/main/java/com/casetest/account/adapter/port/out/rest/AccountRestAdapter.java:62-64, 75-76`

**Description:**
New Gson instances are created on every request/response serialization instead of reusing a single instance.

**Current Implementation:**
```java
public String makeForRequest() {
    return new Gson().toJson(this);
}

public ResponseDTO(String playload) {
    Gson gson = new Gson();
    ResponseDTO dto = gson.fromJson(playload, ResponseDTO.class);
    // ...
}
```

**Performance Impact:**
- Gson instantiation has overhead
- Called on every HTTP request to registration service and central bank
- Unnecessary object creation increases GC pressure

**Suggested Fix:**
Create a static final Gson instance and reuse it:
```java
private static final Gson GSON = new Gson();
```

**Priority:** MEDIUM - HTTP operations but happens frequently

---

## Low Priority Issues

### 6. Inefficient Stream in updateActivities()

**Location:** `account/src/main/java/com/casetest/account/adapter/port/out/persistence/AccountPersistenceAdapter.java:59-66`

**Description:**
The stream filters activities and performs database saves, but could be optimized with batch operations.

**Current Implementation:**
```java
public void updateActivities(Account account) {
    account.getLedger().getActivities().stream()
        .filter(activity -> activity.getId() == null)
        .forEach(activity -> {
            activityRepository.save(new ActivityPersisdente().mapToJpaEntity(account.getId().get(), activity));
        });
}
```

**Performance Impact:**
- Each save is a separate database transaction
- No batch insert optimization
- Creates new mapper instance for each activity

**Suggested Fix:**
Use `saveAll()` for batch insert:
```java
List<ActivityEntity> newActivities = account.getLedger().getActivities().stream()
    .filter(activity -> activity.getId() == null)
    .map(activity -> mapper.mapToJpaEntity(account.getId().get(), activity))
    .collect(Collectors.toList());
activityRepository.saveAll(newActivities);
```

**Priority:** LOW - Only executes on successful transactions with multiple activities

---

### 7. Console Output Instead of Proper Logging

**Location:** `account/src/main/java/com/casetest/account/adapter/port/out/persistence/AccountPersistenceAdapter.java:52-54`

**Description:**
Debug information is printed to System.out instead of using a proper logging framework.

**Current Implementation:**
```java
System.out.println("credital_balance: " + creditalBalance);
System.out.println("debit_balance: " + debitBalance);
System.out.println("balance: " + account.calculateBalance().getValue());
```

**Performance Impact:**
- System.out is synchronized and can cause performance issues under load
- Cannot be configured or disabled in production
- No log levels or structured logging

**Suggested Fix:**
Replace with SLF4J logger:
```java
private static final Logger log = LoggerFactory.getLogger(AccountPersistenceAdapter.class);
// ...
log.debug("Balance calculation - credital: {}, debit: {}, total: {}", 
    creditalBalance, debitBalance, account.calculateBalance().getValue());
```

**Priority:** LOW - Minor performance impact but bad practice

---

### 8. Potential Memory Leak in NOTIFICATION_CACHE

**Location:** `account/src/main/java/com/casetest/account/adapter/port/out/rest/AccountRestAdapter.java:21`

**Description:**
The `NOTIFICATION_CACHE` HashMap is used for circuit breaker fallback but entries are only removed on success, not on failure.

**Current Implementation:**
```java
private final Map<Long, MakeOrder> NOTIFICATION_CACHE = new HashMap<>();

public boolean notifyAboutTransfer(MakeOrder order) {
    NOTIFICATION_CACHE.put(order.sourceAccount().getId(), order);
    // ... HTTP call ...
    NOTIFICATION_CACHE.remove(order.sourceAccount().getId());
    return responseBody.getStatusCode().is2xxSuccessful();
}
```

**Performance Impact:**
- If HTTP call throws exception before remove, entry stays in map forever
- Memory leak over time with failed transactions
- Not thread-safe (no synchronization on HashMap)

**Suggested Fix:**
- Use try-finally to ensure removal
- Or use a proper cache with expiration (Caffeine, Guava Cache)
- Or use WeakHashMap if references can be weak
- Add synchronization for thread safety

**Priority:** LOW - Only grows with failures, but is a memory leak

---

## Summary Statistics

| Priority | Count | Estimated Performance Impact |
|----------|-------|------------------------------|
| High     | 2     | Critical - affects every transaction |
| Medium   | 3     | Moderate - frequent but not critical path |
| Low      | 3     | Minor - best practices and edge cases |

## Recommendations

1. **Immediate Action (This PR):** Fix the Ledger.calculateBalance() double iteration as it's in the critical transaction path
2. **Short Term:** Address the Money.isGreaterThan() bug as it affects business logic correctness
3. **Medium Term:** Optimize the daily limit checking and Gson instantiation
4. **Long Term:** Implement proper logging, batch database operations, and fix memory leak

## Testing Notes

All fixes should maintain existing behavior and pass current tests. The Ledger optimization changes implementation but not functionality - it should produce identical results with better performance.

---

*Report generated during performance audit of minitato/case-test repository*
*Date: October 10, 2025*

package com.casetest.account.adapter.port.out.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "activity")
public class ActivityEntity {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "activity_seq"
    )
    @SequenceGenerator(
        name = "activity_seq",
        initialValue = 1009
    )
    private Long id;
    @Column
    private LocalDateTime whenHappened;
    @Column
    private Long ownerAccountId;
    @Column
    private Long sourceAccountId;
    @Column
    private Long targetAccountId;
    @Column(name = "money")
    private Long value;

    public ActivityEntity() {}


    public ActivityEntity(LocalDateTime whenHappened, Long ownerAccountId, Long sourceAccountId, Long targetAccountId, Long value) {
        this.whenHappened = whenHappened;
        this.ownerAccountId = ownerAccountId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.value = value;
    }

    public ActivityEntity(Long id, LocalDateTime whenHappened, Long ownerAccountId, Long sourceAccountId, Long targetAccountId, Long value) {
        this.id = id;
        this.whenHappened = whenHappened;
        this.ownerAccountId = ownerAccountId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getWhenHappened() {
        return whenHappened;
    }

    public Long getOwnerAccountId() {
        return ownerAccountId;
    }

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public Long getTargetAccountId() {
        return targetAccountId;
    }

    public Long getValue() {
        return value;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWhenHappened(LocalDateTime whenHappened) {
        this.whenHappened = whenHappened;
    }

    public void setOwnerAccountId(Long ownerAccountId) {
        this.ownerAccountId = ownerAccountId;
    }

    public void setSourceAccountId(Long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public void setTargetAccountId(Long targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

    public void setValue(Long value) {
        this.value = value;
    }

}

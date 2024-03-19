#!/bin/bash

# Verifica se foram fornecidos os parâmetros esperados
if [ $# -ne 3 ]; then
    echo "Uso: $0 <sourceName> <targetName> <money>"
    exit 1
fi

# Atribui os parâmetros a variáveis
SOURCE="$1"
TARGET="$2"
MONEY="$3"

# URL de destino com os parâmetros
URL="http://localhost:8080/accounts/transfer/$SOURCE/$MONEY/$TARGET"

# Número de repetições
REPETICOES=20

# Loop para realizar solicitações GET
for ((i=1; i<=$REPETICOES; i++)); do
    echo "Realizando solicitação GET $i"
    # Utiliza curl para fazer a solicitação GET
    curl -s -o /dev/null -w "Resposta: %{http_code}\n" "$URL"
done

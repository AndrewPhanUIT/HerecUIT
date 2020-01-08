#!/bin/bash

function one_line_pem {
    echo "`awk 'NF {sub(/\\n/, ""); printf "%s\\\\\\\n",$0;}' $1`"
}

function json_ccp {
    local PP=$(one_line_pem $5)
    local CP=$(one_line_pem $6)
    sed -e "s/\${ORG}/$1/" \
        -e "s/\${P0PORT}/$2/" \
        -e "s/\${P1PORT}/$3/" \
        -e "s/\${CAPORT}/$4/" \
        -e "s#\${PEERPEM}#$PP#" \
        -e "s#\${CAPEM}#$CP#" \
        -e "s/\${ORGPREFIX}/$7/" \
        ccp-template-herec.json 
}

function yaml_ccp {
    local PP=$(one_line_pem $5)
    local CP=$(one_line_pem $6)
    sed -e "s/\${ORG}/$1/" \
        -e "s/\${P0PORT}/$2/" \
        -e "s/\${P1PORT}/$3/" \
        -e "s/\${CAPORT}/$4/" \
        -e "s#\${PEERPEM}#$PP#" \
        -e "s#\${CAPEM}#$CP#" \
        ccp-template.yaml | sed -e $'s/\\\\n/\\\n        /g'
}

ORG=Client
ORGPREFIX=client
P0PORT=7051
P1PORT=8051
CAPORT=7054
PEERPEM=crypto-config/peerOrganizations/client.herec.uit/tlsca/tlsca.client.herec.uit-cert.pem
CAPEM=crypto-config/peerOrganizations/client.herec.uit/ca/ca.client.herec.uit-cert.pem

echo "$(json_ccp $ORG $ORGPREFIX $P0PORT $P1PORT $CAPORT $PEERPEM $CAPEM)" > connection-herec-client.json
echo "$(yaml_ccp $ORG $ORGPREFIX $P0PORT $P1PORT $CAPORT $PEERPEM $CAPEM)" > connection-herec-client.yaml

ORG=Quan12
ORGPREFIX=quan12
P0PORT=9051
P1PORT=10051
CAPORT=8054
PEERPEM=crypto-config/peerOrganizations/quan12.herec.uit/tlsca/tlsca.quan12.herec.uit-cert.pem
CAPEM=crypto-config/peerOrganizations/quan12.herec.uit/ca/ca.quan12.herec.uit-cert.pem

echo "$(json_ccp $ORG $P0PORT $ORGPREFIX $P1PORT $CAPORT $PEERPEM $CAPEM)" > connection-herec-quan12.json
echo "$(yaml_ccp $ORG $P0PORT $ORGPREFIX $P1PORT $CAPORT $PEERPEM $CAPEM)" > connection-herec-quan12.yaml

#!/bin/bash
scp build/libs/ViSauna-*.jar root@94.198.220.140:/root/viSauna/
scp src/main/resources/log4j2.xml root@94.198.220.140:/root/viSauna/
#scp src/main/resources/application.yaml root@94.198.220.140:/root/viSauna/
ssh root@94.198.220.140 systemctl restart viSauna.service
del *.log
del *json
k6 run --summary-export custom.json custom.js > custom.log
timeout /t 3 /nobreak
k6 run total.js
timeout /t 10 /nobreak
k6 run --summary-export platform.json forkJoin.js > forkJoin.log
timeout /t 3 /nobreak
k6 run total.js
timeout /t 10 /nobreak
k6 run --summary-export virtual.json virtual.js > virtual.log
timeout /t 3 /nobreak
k6 run total.js
timeout /t 10 /nobreak
k6 run --summary-export all.json simultaneo.js > simultaneo.log
timeout /t 3 /nobreak
k6 run total.js

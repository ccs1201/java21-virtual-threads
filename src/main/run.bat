del *.log
del *json
k6 run --summary-export custom.json .\resources\test_scripts\custom.js
timeout /t 3 /nobreak
k6 run .\resources\test_scripts\total.js
timeout /t 10 /nobreak
k6 run --summary-export platform.json .\resources\test_scripts\forkJoin.js
timeout /t 3 /nobreak
k6 run .\resources\test_scripts\total.js
timeout /t 10 /nobreak
k6 run --summary-export virtual.json .\resources\test_scripts\virtual.js
timeout /t 3 /nobreak
k6 run .\resources\test_scripts\total.js
timeout /t 10 /nobreak
k6 run --summary-export all.json .\resources\test_scripts\simultaneo.js
timeout /t 3 /nobreak
k6 run .\resources\test_scripts\total.js

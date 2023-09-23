del *.log
del *json
@REM k6 run --summary-export custom.json .\resources\test_scripts\custom.js
@REM timeout /t 10 /nobreak
@REM k6 run .\resources\test_scripts\total.js
@REM timeout /t 3 /nobreak
@REM k6 run --summary-export platform.json .\resources\test_scripts\forkJoin.js
@REM timeout /t 10 /nobreak
@REM k6 run .\resources\test_scripts\total.js
@REM timeout /t 3 /nobreak
@REM k6 run --summary-export virtual.json .\resources\test_scripts\virtual.js
@REM timeout /t 10 /nobreak
@REM k6 run .\resources\test_scripts\total.js
@REM timeout /t 3 /nobreak
@REM k6 run --summary-export all.json .\resources\test_scripts\simultaneo.js
@REM timeout /t 10 /nobreak
@REM k6 run .\resources\test_scripts\total.js


k6 run --summary-export platform.json .\resources\test_scripts\forkJoinBlocking.js
timeout /t 10 /nobreak

k6 run .\resources\test_scripts\totalBlocking.js
timeout /t 3 /nobreak

k6 run --summary-export virtual.json .\resources\test_scripts\virtualBlocking.js
timeout /t 10 /nobreak

k6 run .\resources\test_scripts\totalBlocking.js
timeout /t 3 /nobreak

k6 run --summary-export simultaneoBlocking.json .\resources\test_scripts\simultaneoBlocking.js
timeout /t 10 /nobreak

k6 run .\resources\test_scripts\totalBlocking.js

@echo off
D:
cd LocalRepo
hg log -l 1 > test
set /p log=<test
set "id=%log:*:=%"
SET VERBOSE=NO
hg status --change %id%
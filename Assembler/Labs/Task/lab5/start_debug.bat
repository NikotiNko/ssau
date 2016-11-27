@echo off
cls
"C:\Users\Саня\Desktop\Программы\ASM\отладчик\DOS\DOSBox\DOSBox.exe" -c "mount c 'I:\учеба\3 курс\1 семестр\ЭВМ и ПУ\'" -c "C:" -c "call lab5\SDD.BAT"
pause
start_debug.bat
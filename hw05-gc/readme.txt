Проведенные тесты -XX:+UseParallelGC, -XX:+UseG1GC
Выводы:
В целом оба алгоритма GC следят за наличием памяти и корректируют частоту запусков сборос мусора.

Работа с молодым поколением идет с начала запуска программы с умеренным кол-ом сборок... По мере уменьшения
остатка памяти, частота сборок  увеличивается до определенного значения (можно повлиять временем сборки). Время сборок относительно
старого поколения быстрое.


Работа со старым поколением проявляется при сокращении объема свободной памяти, время сборок занимает больше времени относительно молодого
поколения.

Разница между -XX:+UseParallelGC  и -XX:+UseG1GC:
В UseParallelGC работа со старым  поколением более длительная(тщательная, параллельная) и наступает раньше(при большем остатке свободной памяти) чем в UseG1GC
Время работы программы при наличии свободной памяти в -XX:+UseG1GC оказалось  выше.
С уменьшением стартового для программы объема выделяемой памяти, время работы сравнялось.
G1GC показал  остановки программы HotStop (10 мс в среднем за раз), тем самым обеспечивая лучшую отзывчивость по сравнению
с ParallelGC. С другой стороны ParallelGC показал более частую, но длительную обработку старого поколения - тем самым вводил в приложение
более длительные времена остановок. Как вывод G1 - предпочтителен для быстрой отзывчивости, ParallelGC - для большей производительности,
затрагивающий больше ресурсов на работу.



-Xms128m -Xmx128m -XX:+UseParallelGC

"C:\Program Files\Java\jdk-11.0.1\bin\java.exe" -Xms128m -Xmx128m -XX:+UseParallelGC "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2018.3\lib\idea_rt.jar=61260:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2018.3\bin" -Dfile.encoding=UTF-8 -classpath C:\Work\MyEducation\Java\Otus\otus_education_java_2019_03\hw05-gc\target\classes ru.otus.test.GcBrenchMain
Hit enter key when ready...
Gathering GC Notification info...
Memory overload. Worked time, sec: 247
Report:
PS MarkSweep:

min: 2; Summary duration (ms):118; Avg duration (ms): 118; gc run count (per min):1
min: 3; Summary duration (ms):295; Avg duration (ms): 147; gc run count (per min):2
min: 4; Summary duration (ms):735; Avg duration (ms): 183; gc run count (per min):4
___________________________

PS Scavenge:

min: 1; Summary duration (ms):54; Avg duration (ms): 27; gc run count (per min):2
min: 2; Summary duration (ms):35; Avg duration (ms): 35; gc run count (per min):1
min: 3; Summary duration (ms):69; Avg duration (ms): 34; gc run count (per min):2
min: 4; Summary duration (ms):59; Avg duration (ms): 19; gc run count (per min):3
___________________________


Process finished with exit code 0



-Xms128m -Xmx128m -XX:+UseG1GC
Gathering GC Notification info...
Memory overload. Worked time, sec: 246
Report:
G1 Young Generation:

min: 0; Summary duration (ms):30; Avg duration (ms): 10; gc run count (per min):3
min: 1; Summary duration (ms):46; Avg duration (ms): 15; gc run count (per min):3
min: 2; Summary duration (ms):30; Avg duration (ms): 10; gc run count (per min):3
min: 3; Summary duration (ms):64; Avg duration (ms): 10; gc run count (per min):6
min: 4; Summary duration (ms):6; Avg duration (ms): 6; gc run count (per min):1
___________________________

G1 Old Generation:

min: 4; Summary duration (ms):119; Avg duration (ms): 59; gc run count (per min):2
___________________________



-Xms32m -Xmx32m -XX:+UseParallelGC
Gathering GC Notification info...
Memory overload. Worked time, sec: 73
Report:
PS MarkSweep:

min: 0; Summary duration (ms):122; gc run count (per min):3
min: 1; Summary duration (ms):177; gc run count (per min):3
___________________________

PS Scavenge:

min: 0; Summary duration (ms):42; gc run count (per min):4
___________________________



-Xms32m -Xmx32m -XX:+UseG1GC
Gathering GC Notification info...
Memory overload. Worked time, sec: 73
Report:
G1 Young Generation:

min: 0; Summary duration (ms):133; gc run count (per min):17
min: 1; Summary duration (ms):74; gc run count (per min):10
___________________________

G1 Old Generation:

min: 1; Summary duration (ms):56; gc run count (per min):2
___________________________


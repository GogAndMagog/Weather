<h1>Погода</h1>

<p>
  Проект из роадмапа <a href="https://zhukovsd.github.io/java-backend-learning-course/projects/weather-viewer/">Сергея Жукова</a>. 
  Представляет из себя сайт с возможностью создания уникальных пользователей, которые могут добавлять и удалять интересующие их локации, а также получать сведения о погоде, на этих локациях.
</p> 


<h2>Деплой</h2>

<p>
  Чтобы развернуть приложение, вам нужно развернуть Postgresql БД (минимум 16-ую версию) и сервер-приложений Tomcat (минимум 10-ую версию)
</p>


<h3>Настройка Postgres</h3>

<p>
  Установить Postgres можно по этому <a href="https://www.digitalocean.com/community/tutorials/how-to-install-postgresql-on-ubuntu-20-04-quickstart">гайду</a>. 
  Необходимо создать пользователя с правами администратора, из под которого приложение будет работать с БД. 
  Т.к. мне было удобнее работать с pgAdmin, чем с консолью, то я открывал стандартный для Postgres порт 5432 командой ufw allow 5432, а таже запускал фаерволл Линукса командой ufw enable, т.к. до этого он запущен небыл.
  Также необходимо разрешить внешним пользователям подключаться к БД, для этого надо отредактировать файлы, например редактором nano.<br/>
</p>

<p>
  nano /etc/postgresql/16/main/postgresql.conf<br/>
  Необходимо добавить вот эту строку:<br/>
<pre>
    #listen_addresses = 'localhost'
    listen_addresses = '*'
</pre>
</p>

<p>
  Также необходимо отредактировать файл pg_hba.conf
</p>

<p>
  nano /etc/postgresql/13/main/pg_hba.conf <br/>
<pre>
# TYPE  DATABASE  USER  ADDRESS    METHOD
host    all       all   0.0.0.0/0  md5
host    all       all   :/0        md5
</pre>
</p>

<p>
  После этого необходимо, через psql или любую другую консоль перезагрузить конфигурацию, командой: SELECT pg_reload_conf(); <br/>
  Далее необходимо перезагрузить сервис командой: systemctl restart postgresql 
</p>

<p>
  Теперь можно коннектиться, через pgAdmin с вашего компьютера к удалённому серверу и создавать пользователя. Всё это можно было сделать, конечно и через psql. 
  Как это всё будет сделано - порт 5432 лучше закрыть, чтобы никто не смог подключаться к вашей БД.
</p>

<h3>Настройка Tomcat</h3>

<p>
  Для установки Tomcat на Ubuntu можно воспользоваться вот этим <a href="https://www.digitalocean.com/community/tutorials/how-to-install-apache-tomcat-10-on-ubuntu-20-04">гайдом</a>. 
  Но, есть ряд моментов, которые надо настроить, чтобы приложение работало корректно
</p>

<p>
  Необходимо, в настройках сервиса Tomcat указать переменные окружения, для Логина/Пароля пользователя БД, через которого приложение будет осуществлять работу с БД. А также указать ключ для API OpenWeather.
</p>

<p>
  Environment="JAVA_OPTS=-Djava.security.egd=file:///dev/urandom -Dspring.profiles.active=Develop -DPOSTGRES_USER=Логин_Пользователя_БД -DPOSTGRES_PASS=Пароль_пользователя_БД -DWEATHER_API_KEY=ключ_для_OpenWeatherAPI>
</p>

<p>
  Т.к. приложение настроено на обработку DELETE http методов, то надо в Tomact включить парсинг таких методов, делается это редактированием файла server.xml
  Необходимо в тэге Connector добавить параметр parseBodyMethods="POST,PUT,DELETE", после этого Tomcat сможет нормально обрабатывать эти запросы.
</p>

<p>
  Когда Tomcat будет настроен, надо открыть стандартный для него порт 8080 коммандой <i>ufw allow 8080</i> и перезагрузить фаерволл коммандой <i>ufw reload</i><br/>
  Теперь можно заходить в админку Tomcat по ip вашего сервера и порту 8080, и деплоить .war-файл. 
</p>

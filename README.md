## Sample Project using  Hibernate + SpringMVC + SpringSecurity

[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy?template=https://github.com/ferzerkerx/albumEntity-finder)
[![Build Status](https://github.com/ferzerkerx/album-finder/actions/workflows/maven.yml/badge.svg)](https://github.com/ferzerkerx/album-finder/actions/workflows/maven.yml/badge.svg)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.ferzerkerx%3AAlbumFinder&metric=alert_status)](https://sonarcloud.io/dashboard/index/com.ferzerkerx%3AAlbumFinder)

### Overview:

- Includes simple CRUD functionality
- Some features are restricted to admin users refer
  to [SecurityWebConfig](https://github.com/ferzerkerx/album-finder/blob/master/src/main/java/com/ferzerkerx/albumfinder/config/SecurityWebConfig.java#L33)
  for a list of valid users

### Backend:

- REST API using SpringBoot
- User authentication is provided by SpringSecurity
- CSRF Protection enabled
- Data Persistence is done using hibernate on a HSQLDB
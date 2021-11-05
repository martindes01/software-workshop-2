# software-workshop-2

## About

These are my exercises and assignment submissions for the Software Workshop 2 (Full-Stack Application Development) module of the MSc Computer Science postgraduate course at the University of Birmingham during the 2020&ndash;2021 session.

## Getting Started

### Prerequisites

These projects use the [PostgreSQL](https://www.postgresql.org/) database management system and the [Gradle](https://gradle.org/) build tool.

All database projects access the default `postgres` database at address `localhost` on port `5432`.
Since some distributions of PostgreSQL require the database to be password-protected, it is assumed that the database is protected by the password `password`.

It is recommended to use the provided Gradle 6.8.2 Wrappers (`gradlew` on Unix and `gradlew.bat` on Windows) to build and run these projects.
Although this method does not require a Gradle installation, it does require the installation of a version of the [Java Development Kit](https://adoptopenjdk.net/) between 11 and 15, inclusive.
It is assumed that the `JAVA_HOME` and `PATH` environment variables are correctly set.

### Installation

Simply clone the source from this repository.

```shell
git clone https://github.com/martindes01/software-workshop-2.git
cd software-workshop-2
```

The projects of `unit-1`, `unit-2`, `unit-5/exercises/exercise-1` and `unit-7` make use of the `music` relations.
The project of `assignment-1` makes use of the `jabber` relations.
These relations can be added to the `postgres` database by executing the following commands in the PostgreSQL interactive terminal.

```shell
\include sql/music/music-def.sql
\include sql/music/music-data.sql
\include sql/jabber/jabber-def.sql
\include sql/jabber/jabber-data.sql
```

The `jabber` relations used by `assignment-2` differ from the original `jabber` relations.
The original relations can be replaced by dropping the original `jabber` relations and adding the altered relations as follows.

```shell
DROP TABLE jabberuser CASCADE;
DROP TABLE jab CASCADE;
DROP TABLE likes CASCADE;
DROP TABLE follows CASCADE;

\include assignment-2/server/jabber-def.sql
\include assignment-2/server/jabber-data.sql
```

This method of dropping and redefining relations can be used to reset any of the `music` and `jabber` relations.

### Exercises

Each unit covers a different topic.
The exercises attempted for each unit can be found in the directory named after that unit.

Unit | Title
---: | ---
1 | Introduction to SQL
2 | Advanced SQL
3 | Database Design and Entity-Relationship Modelling
4 | Unit Testing and JUnit
5 | Database Connectivity and the Client&ndash;Server Model
6 | Threads and Synchronisation
7 | The Multithreaded Server Model
8 | Introduction to JavaFX
9 | Advanced JavaFX and the Model&ndash;View&ndash;Controller Pattern

### Assignment 1

- Instructions: [instructions.pdf](assignment-1/instructions.pdf)
- Last commit before submission: [50d1d63](https://github.com/martindes01/software-workshop-2/commit/50d1d636e79e9eed8637d3b667704e09b52bf9d8)

### Assignment 2

This repository contains my implementation of both the client and the server.
When the group implementation is made public, it will be available [here](https://github.com/martindes01/assignment-2).

- Instructions: [instructions.pdf](assignment-2/instructions.pdf)
- Last commit before submission: [martindes01/assignment-2@5a68c88](https://github.com/martindes01/assignment-2/commit/5a68c885d5369571431f050ee0091d0e00058513)

## Usage

### Assignment 1

To build the project, execute the `build` task in the `assignment-1` directory using the provided Gradle Wrapper.

```shell
cd assignment-1
./gradlew build
```

To run the unit tests, execute the `test` task.
Note that this will reset the `jabber` relations to their initial definitions before the tests are run.

```shell
./gradlew test
```

### Assignment 2

To build the project, execute the `build` task in the `assignment-2` directory using the provided Gradle Wrapper.

```shell
cd assignment-2
./gradlew build
```

To start the server, execute the `run` task of the `server` subproject.
The server will attempt to listen for incoming connections on port `44444`.

```shell
./gradlew :server:run
```

To start an instance of the client, execute the `run` task of the `client` subproject from another terminal.
The client will attempt to connect to the server at address `localhost` on port `44444`.

```shell
./gradlew :client:run
```

## License

This project is distributed under the terms of version 3 of the GNU General Public License as published by the Free Software Foundation.
See [COPYING](COPYING) for more information.

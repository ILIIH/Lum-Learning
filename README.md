# **Memorizable**

## **1. Overview.**

The Android application centered around personalized learning harnesses the power of a neural network, enabling the creation of a highly tailored and adaptive learning experience for the user.

## **Contents:**

1. [Project Overview](#1-overview)
2. [UML diagram](#2-uml-diagram)
3. [Netural network topologies](#3-to-be-diagram)
4. [Software architecture overview](#4-software-architecture-overview)
5. [Features](#5-features)
   - [User registration](#user-registration)
   - [Find accommodation](#find-accommodation)
   - [Find airlines flights](#find-airlines-flights)
   - [Find local bus tickets](#find-local-bus-tickets)
   - [Find attraction tickets](#find-attraction-bus-tickets)
   - [Tables reservation in a restaurants](#tables-reservation)
   - [Excursions booking ](#excursions-booking)
   - [Account Page](#account-page)
   - [Register, Login/logout](#register-loginlogout-pages)
   - [Basket Page](#shopping-basket)
   - [Checkout Page](#checkout-page)

## \*Project Overview:\*\*

In today's information age, we are constantly surrounded by various forms of information. Success isn't merely about understanding a topic; it requires comprehensive knowledge of all aspects related to it. The primary goal of this application is to provide users with the opportunity to enhance their learning speed, allowing them to grasp a wide range of information quickly and effectively, empowering them to thrive in this fast-paced digital era.

## **2. UML diagram:**

The Matrix class is responsible for storing data about a matrix and provides access to auxiliary functions to perform mathematical operations on this matrix. MatrixOps is an auxiliary class with static functions to perform complex mathematical operations on matrices. The Dense class is responsible for performing forward actions on a layer. The Activation interface is an interface that has two functions: 'call' and 'gradient' to perform operations on matrices and calculate gradients. The main class in this context is the Model class, which is responsible for providing a neural network model creation interface for programmers

![UML diagram](Documentation/images/ClassesUML.png)

## **3. TO - BE diagram:**

This process can be easily automated by our robot.

![TO - BE diagram](Documentation/images/to-be-diagram.png)

## **4. Software architecture overview:**

High-level architecture. Our robot, you can see that there are 7 feature sub-modules, each of which corresponds to one sprint. Separately, Dispatcher and Performer were developed for each of the modules.

Core Modeule . As you can see from the diagram, it is the most independent module which contains 2 layers:

1.  UI - contains processes that can be used many times in different modules and that interact with the user interface
2.  Data - contains processes that can be used many times in different modules and that interact exclusively with data

The Database module. This modile contains sequences of interaction with a relational database and consists of two layers:

1.  Tables - encapsulates the logic of creating and managing databases tables
2.  DAO (Data Access Object) - contains the logic of interaction with data that contains in data tables

The Data Analytics module contains the neural network training logic and the data analysis logic; it consists of 2 layers:

1.  Machine learning - encapsulates the logic of interaction with a neural network
2.  Calculations - contains the calculation logic for data analysis

![ARCHITECTURE DIAGRAM](Documentation/images/architecture_diagram.png)

Lower level architecture. Each feature module consists of three layers:

1.  Dispatcher - it delivers data from different sources
2.  Performer - processes data provided by DispatcherEach of these salts is divided into sublayers
    - Model - it contains sequences that directly interact with data models, transform and process them
    - View - there are sequences that interact with I / O interfaces
    - Controller - there are sequences that connect Model and View and perform intermediate data manipulations
3.  Tests - here the testing sequences can be divided into 3 sub-layers:
    - UI tests - they test the correct interaction of processes with a visual display
    - Integration tests - test the correctness of the interaction between Model , View , Controller under layers
    - Unit testing - tests each sequence in isolation for a specific job

![FEATURE ARCHITECTURE DIAGRAM](Documentation/images/feature_architecture_diagram.png)

## **5. Features.**

### User registration

With this function, the user creates an owl profile, fills in the data about the trips he wants to make, we save this data and process it in the future with the help of performers.

### Find accommodation

In process

### Find airlines flights

In process

### Find local bus tickets

In process

### Find attraction tickets

In process

### Tables reservation in a restaurants

In process

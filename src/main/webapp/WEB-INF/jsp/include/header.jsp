<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="/pub/css/default.css" type="text/css">
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
    <title>ScoreKeeper</title>
</head>
<body>
	<sec:authorize access="!isAuthenticated()">
      <a href="/login">Login</a> | <a href="/register">Register</a>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
      <a href="/dashboard">Dashboard</a> | <a href="/logout">Logout</a>
    </sec:authorize>
    <h1>ScoreKeeper Alpha</h1>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
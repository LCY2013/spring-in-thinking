<?xml version="1.0" encoding="UTF-8" ?>
<jsp:directive.page language="java" contentType="text/html; charset=UTF-8"
                    pageEncoding="UTF-8" />
<html>
<head>
    <link rel="stylesheet" href="<spring:theme code='styleSheet'/>" type="text/css"/>
</head>
<body>
\${user.name} : ${user.name}
\${user.id} : ${user.id}
\${applicationScope['scopedTarget.createUser'].name} : ${applicationScope['scopedTarget.createUser'].name}
\${applicationScope['scopedTarget.createUser'].id} : ${applicationScope['scopedTarget.createUser'].id}
</body>
</html>
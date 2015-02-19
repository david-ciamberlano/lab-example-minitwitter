<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Mini Twitter</title>
</head>
<body>

    <h3 id="message">${message}</h3>

    <section id="tweetlist">
        <c:forEach  items="${tweets}" var="tweet">
            <div>${tweet}</div>
        </c:forEach>
    </section>

</body>
</html>

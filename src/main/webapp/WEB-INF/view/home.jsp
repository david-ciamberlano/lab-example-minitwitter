<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Mini Twitter</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"></c:url>" />
    <link href='http://fonts.googleapis.com/css?family=Lobster' rel='stylesheet' type='text/css'>
</head>
<body>

    <header>
        <h1 id="message">&micro;Twitter</h1>
        <h2>${pagetype}</h2>
        <h3>${newTweet.user}</h3>
    </header>
    <section id="addnew">
        <form:form modelAttribute="newTweet" action="${user}/add">
            <div>
                Cosa c'&egrave; di nuovo?
                <form:hidden path="user"/>
                <form:input size="65" maxlength="140" path="text" id="text" />
                <input type="submit" id="btnSubmit" value="tweet"/>
            </div>
        </form:form>
    </section>
    <section id="links">

    </section>

    <main id="tweetlist">
        <c:forEach  items="${tweets}" var="tweet">
            <article class="tweet">
                <jsp:useBean id="dateObject" class="java.util.Date" />
                <jsp:setProperty name="dateObject" property="time" value="${tweet.timestamp}" />
                <div class="tweet_info">twittato da <strong>@${tweet.user}</strong> il <fmt:formatDate value="${dateObject}" pattern="dd/MM/yyyy" /> alle <fmt:formatDate value="${dateObject }" pattern="hh:mm" /></div>
                <div class="tweet_text">${tweet.text}</div>
            </article>
        </c:forEach>
    </main>

</body>
</html>

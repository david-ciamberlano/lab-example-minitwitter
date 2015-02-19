<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title></title>
</head>
<body>
    <section id="container">
        <form:form modelAttribute="newTweet" >
            <fieldset>
                <legend>Cos'hai da dire?</legend>
                <div>
                    <form:hidden path="user" id="user"></form:hidden>
                    <label for="text">Testo</label>
                    <form:input path="text" id="text" type="text"/>
                    <input type="submit" id="btnSubmit" value="add"/>
                </div>
            </fieldset>
        </form:form>
    </section>
</body>
</html>

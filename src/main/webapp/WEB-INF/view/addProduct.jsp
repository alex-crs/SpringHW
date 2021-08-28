<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>

<html>
<body>
<form:form action="showResult" modelAttribute="product">
    <h2>Add product to base</h2>
    <hr>
    Product name: <br> <form:input path="title" />
    <br>
    Cost: <br> <form:input path="cost" />
    <br>
    <input type="submit" value="Submit" />
    <hr>
</form:form>
</body>
</html>
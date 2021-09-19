<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    <!DOCTYPE html>

    <html>

    <body>
        <a href="menuAction">Back to main menu</a>
        <form:form action="deleteResult" modelAttribute="product">
            <h2>Find product by [Product id]:</h2>
            <hr>
            Product id: <br>
            <form:input path="id" />
            <br>
            <input type="submit" value="Submit" />
            <hr>
        </form:form>
    </body>

    </html>
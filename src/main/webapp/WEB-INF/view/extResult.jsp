<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <!DOCTYPE html>

    <html>

    <body>
        <a href="menuAction">Back to main menu</a> <br>
        <h1>"${action}"</h1>

        <ol>
            <c:forEach var="item" items="${products}">
                <hr>
                <li>id = ${item.id},<br> ${item.title} cost - ${item.cost}</li>
                <hr>
            </c:forEach>
        </ol>

    </body>

    </html>
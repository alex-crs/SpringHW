<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <!DOCTYPE html>

    <html>

    <body>
        <a href="menuAction">Back to main menu</a> <br>
        <h1>Product list:</h1>
        <form action="typedProductList" modelAttribute="searchInt">
            <hr>
            <h3>Cost interval:</h3>
            MinCost: <br>
            <input type="number" name="minCost" value="${minCost}" />
            <br>
            MaxCost: <br>
            <input type="number" name="maxCost" value="${maxCost}" />
            <br>
            Sort type:
            <input type="radio" name="sortType" value="ASC"> Min cost up
            <input type="radio" name="sortType" value="DESC"> Max cost up
            <br>
            Page: <br>
            <input type="number" name="page" value="${page}">
            <br>
            <input type="submit" value="Submit" />
            <hr>
        </form>
        <ol>
            <c:forEach var="item" items="${products}">
                <hr>
                <li>id = ${item.id},<br> ${item.title} cost - ${item.cost}</li>
                <hr>
            </c:forEach>
        </ol>

    </body>

    </html>
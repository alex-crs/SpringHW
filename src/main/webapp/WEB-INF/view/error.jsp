<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css" />
</head>

<body>
<h2>Product with id-${product.id} not found!</h2>
<a href="productList">Show product list</a><br>
<a href="menuAction">Back to main menu</a>
</body>
</html>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/resume_list.css">
    <title>Resume Edit Failed</title>
</head>
<jsp:include page="/WEB-INF/fragments/header.html"/>
<body>
<h1>You've set incorrect Date format for the ${company_name}!<br/>
    Please, set the Dates carefully.
</h1>
<p style="text-align: center;">
    <a href="resume?uuid=${uuid}&action=view">View Resume</a>
    <a href="resume?uuid=${uuid}&action=edit">Edit Resume</a>
</p>
</body>
<jsp:include page="/WEB-INF/fragments/footer.html"/>
</html>
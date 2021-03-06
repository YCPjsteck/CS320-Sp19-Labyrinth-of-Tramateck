<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


        <html>
            <head>
                <title>Text Based</title>
                <link href="../resources/css/style.css" rel="stylesheet" type="text/css">
                <link href="https://fonts.googleapis.com/css?family=Share+Tech+Mono" rel="stylesheet">
                <style>
                    * {
                        font-family: 'Share Tech Mono', monospace;
                        font-weight: 300;
                    }
                    body {
                        background-color: black;
                    }

                    .error {
                        color: red;
                    }

                    td.label {
                        text-align: right;
                    }

                    input[type=text], select {
                        width: 100%;
                        background-color: black;
                        color: #39FF14;
                        border-color: #39FF14;
                        border-style: solid;
                        outline: none;
                        display: border-box;
                    }

                    input[type=submit] {
                        width: 100%;
                        background-color: black;
                        color: #39FF14;
                        border: 2px solid #39FF14;
                        border-radius: 0px;
                        cursor: pointer;
                        display: border-box;
                        outline: none;
                    }
                    
                    hr { display: block; height: 1px;
                        border: 0; border-top: 1px solid #39FF14;
                        margin: 1em 0; padding: 0; 
                    }
                    
                    #contentGrid {
                        display: grid;
                        align-content: center;
                    }

                    #outputBox {
                        border-color: #39FF14;
                        color: #39FF14;
                        background-color:black;
                    }

                    #inputBox {
                        display: grid;
                        grid-template-columns: 4fr 1fr;
                        background color: black;
                    }
                </style>
                <script language="javascript" type="text/javascript">
                    window.onload=toBottom;
                    function toBottom(){
                        window.scrollTo(0, document.body.scrollHeight);
                    } 
                </script>
            </head>

            <body>
                <c:if test="${! empty errorMessage}">
                    <div class="error">${errorMessage}</div>
                </c:if>

                <form action="${pageContext.servletContext.contextPath}/textBased" method="post">
                    <div id="contentGrid">
                        <div id="outputBox">
                            <!-- <div id="outputScreen">${output}</div> -->
                            <table>
                                <c:forEach items="${output}" var="log">
                                    <tr>
                                        <td>${log}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <div id="inputBox">
                            <input type="text" name="input" value="${input}">
                            <input type="Submit" name="submit" value="Submit" >
                        </div>
                    </div>
                    
                    <input name ="playerStr" type="hidden" value="${playerStr}">
                    <input name ="roomStr" type="hidden" value="${roomStr}">
                    <input name ="npcStr" type="hidden" value="${npcStr}">
                    <input name ="eventStr" type="hidden" value="${eventStr}">
                    <input name ="hiddenOutput" type="hidden" value="${hiddenOutput}">
                </form>
            </body>
        </html>

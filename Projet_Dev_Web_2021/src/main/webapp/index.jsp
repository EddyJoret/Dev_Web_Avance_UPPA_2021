<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Fredericka+the+Great&display=swap" rel="stylesheet"> 
    </head>
    <body style="background-image: url('./img/parchemin2.jpg'); background-repeat: no-repeat; background-size: cover; font-family: 'Fredericka the Great', cursive;">
        <%--
        <%! public boolean init = false; %> <!--Variable globale-->
        <%! 
            public boolean submitValue(){
                System.out.println("submit");
                return true;
            }
        %>--%>
        <h1 style="text-align: center; font-size: 50px">Le jeu du cul de chouette</h1>
        <div style="text-align: center; margin-top: 10%">
            <a href="connexionJeu.jsp" style="font-size: 30px" target="_blank">Jouer</a>  
        </div>
        <div class="img-container" style="text-align: center; margin-top: 70px"> <!-- Block parent element -->
            <img src="./img/dice.png" style=""/>
        </div>
        
        
        <%-- 
            if(init){
                String login = request.getParameter( "username" );
                if(login == null){
                    System.out.println("a completer");
                }else{
                    System.out.println(login);
                    if(login.equals("dd")){
                        System.out.println("ok");
                    }
                }
            }else{
                init = true;
                System.out.println("init");
            }
            
            
        --%>
    </body>
</html>

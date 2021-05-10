<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%--
        <%! public boolean init = false; %> <!--Variable globale-->
        <%! 
            public boolean submitValue(){
                System.out.println("submit");
                return true;
            }
        %>--%>
        <h1 style="text-align: center">Le jeu du cul de chouette</h1>
        <div style="text-align: center; margin-top: 10%">
            <a href="connexionJeu.jsp" style="font-size: 20px">Jouer</a>  
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

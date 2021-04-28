<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%! public boolean init = false; %> <!--Variable globale-->
        <%! 
            public boolean submitValue(){
                System.out.println("submit");
                return true;
            }
        %>
        <h1>Hello World!</h1>
        <form name="loginForm" method="post" action="Joueur">
               Nom: <input type="text" name="username"/> <br/>
               rue: <input type="text" name="rue"/> <br/>
               Code postal: <input type="text" name="cp"/> <br>
               Ville: <input type="text" name="ville"/> <br>
               <input type="submit" value="Login" onclick<%=submitValue()%>/>
        </form>
        <% 
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
            
            
        %>
    </body>
</html>

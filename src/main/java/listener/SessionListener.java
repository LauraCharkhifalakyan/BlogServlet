package listener;

import model.User;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener
public class SessionListener implements HttpSessionAttributeListener {

    public void attributeAdded(HttpSessionBindingEvent event) {
        if(event.getName().equals("user")){
            User value = (User) event.getValue();
            String txt = "User with %s %s logged in";
            System.out.println(String.format(txt, value.getName(), value.getSurname()));
        }
    }

    public void attributeRemoved(HttpSessionBindingEvent event) {
        System.out.println("attribute removed");
    }
}

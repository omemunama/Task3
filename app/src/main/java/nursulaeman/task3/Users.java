package nursulaeman.task3;

import com.google.common.hash.HashCode;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nur on 21/09/16.
 */
public class Users {
    @SerializedName("users")
    public List<UserItem> users;
    public List<UserItem> getUsers() { return users; }
    public void setUsers(List<UserItem> users) { this.users = users; }
    public Users(List<UserItem> users) { this.users = users; }

    public class UserItem {
        private int id;
        private String email;
        private String name;
        private String password;
        private String token_authentication;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getToken_authentication() {
            return token_authentication;
        }

        public void setToken_authentication(String token_authentication) {
            this.token_authentication = token_authentication;
        }

    }
}
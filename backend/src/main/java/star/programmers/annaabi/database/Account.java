package star.programmers.annaabi.database;

import javax.persistence.*;

@Entity
public class Account
{
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private Long id;
    @Column(unique=true)
    private String name;
    private String password;
    @Column(unique=true)
    private String email;
    private String token;


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
}

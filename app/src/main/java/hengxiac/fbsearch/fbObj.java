package hengxiac.fbsearch;

/**
 * Created by hengxiang1 on 2017/4/24.
 */

public class fbObj {
    private String profile;
    private String name;
    private String id;
    private String type;
    private boolean favorite;

    fbObj()
    {
        profile = "";
        name ="";
        id="";
        type="";
        favorite=false;
    }
    fbObj(String type ,String profile, String name, String id, boolean favorite){
        this.type = type;
        this.profile = profile;
        this.name = name;
        this.id = id;
        this.favorite = favorite;
    }
    public String getProfile()
    {
        return profile;
    }
    public String getName()
    {
        return name;
    }
    public String getId()
    {
        return id;
    }
    public String getType()
    {
        return type;
    }
    public boolean getFavorite()
    {
        return favorite;
    }
    public void setProfile(String profile)
    {
        this.profile = profile;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public void setFavorite(boolean favorite)
    {
        this.favorite = favorite;
    }


}

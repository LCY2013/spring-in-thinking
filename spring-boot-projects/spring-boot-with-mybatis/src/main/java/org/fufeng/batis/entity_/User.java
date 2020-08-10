package org.fufeng.batis.entity_;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.id
     *
     * @mbg.generated Fri Aug 07 15:10:57 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.name
     *
     * @mbg.generated Fri Aug 07 15:10:57 CST 2020
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.age
     *
     * @mbg.generated Fri Aug 07 15:10:57 CST 2020
     */
    private Integer age;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.desc
     *
     * @mbg.generated Fri Aug 07 15:10:57 CST 2020
     */
    private String description;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.height
     *
     * @mbg.generated Fri Aug 07 15:10:57 CST 2020
     */
    private Float height;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.id
     *
     * @return the value of user.id
     *
     * @mbg.generated Fri Aug 07 15:10:57 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.id
     *
     * @param id the value for user.id
     *
     * @mbg.generated Fri Aug 07 15:10:57 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.name
     *
     * @return the value of user.name
     *
     * @mbg.generated Fri Aug 07 15:10:57 CST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.name
     *
     * @param name the value for user.name
     *
     * @mbg.generated Fri Aug 07 15:10:57 CST 2020
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.age
     *
     * @return the value of user.age
     *
     * @mbg.generated Fri Aug 07 15:10:57 CST 2020
     */
    public Integer getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.age
     *
     * @param age the value for user.age
     *
     * @mbg.generated Fri Aug 07 15:10:57 CST 2020
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.desc
     *
     * @return the value of user.desc
     *
     * @mbg.generated Fri Aug 07 15:10:57 CST 2020
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.desc
     *
     * @param description the value for user.desc
     *
     * @mbg.generated Fri Aug 07 15:10:57 CST 2020
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.height
     *
     * @return the value of user.height
     *
     * @mbg.generated Fri Aug 07 15:10:57 CST 2020
     */
    public Float getHeight() {
        return height;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.height
     *
     * @param height the value for user.height
     *
     * @mbg.generated Fri Aug 07 15:10:57 CST 2020
     */
    public void setHeight(Float height) {
        this.height = height;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Fri Aug 07 15:10:57 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", age=").append(age);
        sb.append(", description=").append(description);
        sb.append(", height=").append(height);
        sb.append("]");
        return sb.toString();
    }
}
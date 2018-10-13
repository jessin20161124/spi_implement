package com.baobao;

/**
 * Hello world!
 *
 */
public class App 
{
    /**
     *     <!-- 实现者依赖于api，导入实现者就会自动导入api，实际使用者可以把这两个都导入 -->
     * @param args
     */
    public static void main( String[] args )
    {
        SpiFactory.getSpi().say();
    }
}

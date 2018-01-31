/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanny_ageGroup;

import java.sql.SQLException;
import javax.swing.AbstractListModel;
import java.util.ArrayList;

/**
 *
 * @author DThanh
 */
public class MyListModelAll1 extends AbstractListModel {

    public MyListModelAll1(String connect, int nannyCode) throws SQLException {
        super();

        this.connect = connect;
        this.nannyCode = nannyCode;
        dao = new DataAccessList(connect);
        content = new ArrayList();
        getListContent(this.nannyCode);

    }

    public void getListContent(int ageCode) throws SQLException {
        content = dao.showAgeGroupAll(ageCode);
    }

    public int getSize() {
        return content.size();
    }

    public Object getElementAt(int index) {
        return content.get(index);
    }
    String connect;
    int nannyCode;
    ArrayList content;
    private DataAccessList dao = null;
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ageGroup.business;

import java.sql.SQLException;
import javax.swing.AbstractListModel;
import ageGroup.dataObject.AgeGroupDAO;
import java.util.ArrayList;

/**
 *
 * @author DThanh
 */
public class MyListModelAll extends AbstractListModel {

    public MyListModelAll(String connect, int ageCode) throws SQLException {
        super();

        this.connect = connect;
        this.ageCode = ageCode;
        dao = new AgeGroupDAO(connect);
        content = new ArrayList();
        getListContent(ageCode);

    }

    public void getListContent(int ageCode) throws SQLException {
        content = dao.showActivityAll(ageCode);
    }

    public int getSize() {
        return content.size();
    }

    public Object getElementAt(int index) {
        return content.get(index);
    }
    String connect;
    int ageCode;
    ArrayList content;
    private AgeGroupDAO dao = null;
}

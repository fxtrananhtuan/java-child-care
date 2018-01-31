/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ageGroup.business;

import ageGroup.valuesObject.AgeGroupVO;
import ageGroup.dataObject.AgeGroupDAO;

/**
 *
 * @author DThanh
 */
public class AgeGroupManager {

    private AgeGroupDAO ageDAO;

    public AgeGroupManager(String connect) {
        ageDAO = new AgeGroupDAO(connect);
    }

    public void updateData(AgeGroupVO ageVO) {
        ageDAO.updateRecord(ageVO);
    }

    public void insetData(AgeGroupVO ageVO) {
        ageDAO.insertRecord(ageVO);
    }

    public void deleteData(AgeGroupVO ageVO) {
        ageDAO.deleteReord(ageVO);
    }
}

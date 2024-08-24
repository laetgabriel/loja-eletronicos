package org.acgprojeto.controller;

import org.acgprojeto.dao.AdminDAO;
import org.acgprojeto.dao.impl.AdminDAOImpl;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.AdminDTO;


public class AdminController {

    AdminDAO adminDAO = new AdminDAOImpl(DB.getConexao());

    public void inserirAdmin(AdminDTO adminDTO) {
        adminDAO.inserirAdmin(adminDTO);
    }

    public void atualizarAdmin(AdminDTO adminDTO) {
        adminDAO.atualizarAdmin(adminDTO);
    }

    public void excluirAdmin(Integer id) {
        adminDAO.excluirAdmin(id);
    }

    public AdminDTO buscarAdminPorId(Integer id) {
        return adminDAO.buscarAdminPorId(id);
    }

}

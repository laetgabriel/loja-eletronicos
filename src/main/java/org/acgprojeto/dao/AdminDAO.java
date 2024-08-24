package org.acgprojeto.dao;

import org.acgprojeto.dto.AdminDTO;

public interface AdminDAO {

    void inserirAdmin(AdminDTO adminDTO);
    void atualizarAdmin(AdminDTO adminDTO);
    void excluirAdmin(Integer id);
    AdminDTO buscarAdminPorId(Integer idAdmin);

}

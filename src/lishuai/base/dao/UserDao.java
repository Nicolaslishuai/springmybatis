package lishuai.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import lishuai.base.entity.UserEntity;
public interface UserDao {
	
	public UserEntity findById(String Id);
	
	public UserEntity findByName(String name);
	/**
	 * 1.�Զ����ҳ��ѯ
	 * 2.���������
	 * @param userEntity
	 * @param startNo
	 * @param pageSize
	 * @return
	 */
	public List<UserEntity> selectByPage(@Param("userEntity")UserEntity userEntity,@Param("startNo")int startNo,@Param("pageSize")int pageSize);
	/**
	 * ��������ѯ��¼,����Ϸ�ҳ���ʹ��
	 * @param userEntity
	 * @return
	 */
	public List<UserEntity> selectList(UserEntity userEntity);
	/**
	 * ����һ����¼
	 * @param userentity
	 */
	public void insertUserEntity(UserEntity userentity);
	/**
	 * �������������¼
	 * @param list
	 */
	public void addUserEntityBatch(List<UserEntity> list);
	/**
	 * �޸�һ����¼
	 * @param userentity
	 */
	public void updateUserEntity(UserEntity userentity);

}

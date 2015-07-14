package lishuai.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import lishuai.base.entity.UserEntity;
public interface UserDao {
	
	public UserEntity findById(String Id);
	
	public UserEntity findByName(String name);
	/**
	 * 1.自定义分页查询
	 * 2.传多个参数
	 * @param userEntity
	 * @param startNo
	 * @param pageSize
	 * @return
	 */
	public List<UserEntity> selectByPage(@Param("userEntity")UserEntity userEntity,@Param("startNo")int startNo,@Param("pageSize")int pageSize);
	/**
	 * 按条件查询记录,可配合分页插件使用
	 * @param userEntity
	 * @return
	 */
	public List<UserEntity> selectList(UserEntity userEntity);
	/**
	 * 插入一条记录
	 * @param userentity
	 */
	public void insertUserEntity(UserEntity userentity);
	/**
	 * 批量插入多条记录
	 * @param list
	 */
	public void addUserEntityBatch(List<UserEntity> list);
	/**
	 * 修改一条记录
	 * @param userentity
	 */
	public void updateUserEntity(UserEntity userentity);

}

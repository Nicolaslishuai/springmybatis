package lishuai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import lishuai.base.dao.UserDao;
import lishuai.base.entity.UserEntity;
@Service("userservice")
public class Userservice {
	
	@Autowired
	private UserDao userdao;
	
	public UserEntity findby(String id){
		return userdao.findById(id);
	}
	/**
	 * 利用分页插件分页
	 * @param userEntity
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<UserEntity> selectPage(UserEntity userEntity,int pageNo,int pageSize){
		PageHelper.startPage(pageNo, pageSize, true);
		Page<UserEntity>page=(Page<UserEntity>)userdao.selectList(userEntity);
		return page;
	}
	/**
	 * 抛出RuntimeException异常时仍可提交
	 * @param user
	 * @throws Exception
	 */
	public void insertUser(UserEntity user) throws Exception{
		userdao.insertUserEntity(user);
		 //throw new NullPointerException("null");
		 throw new RuntimeException("runtime");
	}
	/**
	 * 抛出NullPointerException异常时回滚
	 * @param list
	 */
	public void addUserBatch(List<UserEntity> list){
		userdao.addUserEntityBatch(list);
		throw new NullPointerException("null");
	}
	public void update(UserEntity user) throws Exception{
		userdao.updateUserEntity(user);
	}

}

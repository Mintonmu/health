package top.beansprout.health.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.beansprout.health.constant.SysConstant;
import top.beansprout.health.mapper.TUserMapper;
import top.beansprout.health.model.dto.UserLoginDto;
import top.beansprout.health.model.dto.UserRegisterDto;
import top.beansprout.health.model.dto.UserUpdateInfoDto;
import top.beansprout.health.model.dto.UserUpdatePasswordDto;
import top.beansprout.health.model.entity.TUser;
import top.beansprout.health.model.vo.BusinessException;
import top.beansprout.health.model.vo.UserInfoVo;
import top.beansprout.health.model.vo.UserLoginVo;
import top.beansprout.health.service.UserService;
import top.beansprout.health.util.PublicUtils;

/**
 * <p> Title: UserServiceImpl </p>
 * <p> Description: 用户业务逻辑操作</p>
 *
 * @Auther: cyy
 * @Date: 2020年4月25日 下午8:02:42
 * @Version: 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TUserMapper userMapper;

    @Override
    public UserLoginVo login(UserLoginDto userLoginDto) {
        final TUser user = userMapper.selectByusername(userLoginDto.getUsername());
        if (PublicUtils.isBlank(user))
            throw new BusinessException("用户不存在");
        if (!user.getPassword().equalsIgnoreCase(userLoginDto.getPassWord()))
            throw new BusinessException("密码不正确");

        final UserLoginVo userLoginVo = new UserLoginVo();
        PublicUtils.copyBean(user, userLoginVo);
        request.getSession().setAttribute(SysConstant.INIT_FIELD_USER_VO, userLoginVo);
        return userLoginVo;
    }

    @Override
    @Transactional
    public void register(UserRegisterDto userRegisterDto) {
        if (!userRegisterDto.getPassWord().equalsIgnoreCase(userRegisterDto.getConfirmPassWord()))
            throw new BusinessException("两次密码不一致");
        final TUser user = userMapper.selectByusername(userRegisterDto.getUsername());
        if (PublicUtils.isNotBlank(user))
            throw new BusinessException("该账户已经注册");
        final TUser tUser = new TUser();
        tUser.setUsername(userRegisterDto.getUsername());
        tUser.setNickname(tUser.getUsername());
        tUser.setPassword(userRegisterDto.getPassWord());
        userMapper.insertByOne(tUser);
    }

    @Override
    public void logout(HttpServletRequest request) {
        // 清除session
        final Enumeration<String> em = request.getSession().getAttributeNames();
        while (em.hasMoreElements()) {
            request.getSession().removeAttribute(em.nextElement());
        }
        request.getSession().invalidate();
        // 请求初始化信息
        request.removeAttribute(SysConstant.INIT_FIELD_REQUEST_VO);
    }

    @Override
    public void updatePassword(HttpServletRequest request, int userId, UserUpdatePasswordDto updatePasswordDto) {
        if (!updatePasswordDto.getPassWord().equalsIgnoreCase(updatePasswordDto.getConfirmPassWord()))
            throw new BusinessException("两次密码不一致");
        final TUser user = userMapper.selectById(userId);
        if (PublicUtils.isBlank(user))
            throw new BusinessException("该账户不存在");
        user.setPassword(updatePasswordDto.getPassWord());
        userMapper.updateByOne(user);
        // 清空session数据
        logout(request);
    }

    @Override
    public UserInfoVo updateUserInfo(int userId, UserUpdateInfoDto userUpdateInfoDto) {
        // 原始文件名
        String sourceName = userUpdateInfoDto.getHeadurl().getOriginalFilename();
        final String fileType = sourceName.substring(sourceName.lastIndexOf("."));
        if (!".jpg".equals(fileType.toLowerCase()) && !".png".equals(fileType.toLowerCase()))
            throw new BusinessException("只能上传jpg、png格式的图片");

        // 获取文件上传的路径，在webapp下的static/upload/中
        final String base = request.getSession().getServletContext().getRealPath("static/upload/");
        final File fileDir = new File(base);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        // 讲文件上传到临时目录
        sourceName = PublicUtils.join(PublicUtils.randomString(10), fileType);
        final File upload = new File(PublicUtils.join(base, sourceName));
        try {
            userUpdateInfoDto.getHeadurl().transferTo(upload);
        } catch (final IOException e) {
            throw new BusinessException("上传错误");
        }

        final String relativePath = PublicUtils.join("static/upload/", sourceName);

        final TUser user = userMapper.selectById(userId);
        if (PublicUtils.isBlank(user))
            throw new BusinessException("该账户不存在");

        user.setNickname(userUpdateInfoDto.getNickname());
        user.setEmail(userUpdateInfoDto.getEmail());
        user.setHeadurl(relativePath);
        userMapper.updateByUserInfoOne(user);

        final UserInfoVo userInfo = new UserInfoVo();
        PublicUtils.copyBean(user, userInfo);
        refreshSessionUserInfo(userInfo);

        return userInfo;
    }

    /**
     * 刷新session中的用户信息
     **/
    private void refreshSessionUserInfo(UserInfoVo userInfo) {
        final HttpSession session = request.getSession();
        final UserLoginVo userLoginVo = (UserLoginVo) session.getAttribute(SysConstant.INIT_FIELD_USER_VO);
        PublicUtils.copyBean(userInfo, userLoginVo);

        session.setAttribute(SysConstant.INIT_FIELD_USER_VO, userLoginVo);
    }

}
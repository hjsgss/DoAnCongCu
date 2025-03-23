package com.GaoQue.controller;

import com.GaoQue.dto.UserDto;
import com.GaoQue.exceptions.AlreadyExistsException;
import com.GaoQue.model.User;
import com.GaoQue.request.CreateUserRequest;
import com.GaoQue.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Tài khoản hoặc mật khẩu không đúng!!");
        }
        return "User/login";
    }

    @GetMapping("/register")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new CreateUserRequest());
        return "/User/register";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute("user") @Valid CreateUserRequest request,
                             BindingResult bindingResult,
                             @RequestParam("confirmPassword") String confirmPassword,
                             Model model) {

        // Kiểm tra mật khẩu có khớp không
        if (!request.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu không khớp!!");
            return "/User/register";
        }

        // Kiểm tra mật khẩu có đủ các yêu cầu không
        String password = request.getPassword();

        // Kiểm tra mật khẩu có ít nhất một chữ cái in hoa
        if (!password.matches(".*[A-Z].*")) {
            model.addAttribute("error", "Mật khẩu phải chứa ít nhất một chữ cái in hoa.");
            return "/User/register";
        }

        // Kiểm tra mật khẩu có ít nhất một chữ số
        if (!password.matches(".*\\d.*")) {
            model.addAttribute("error", "Mật khẩu phải chứa ít nhất một chữ số.");
            return "/User/register";
        }

        // Kiểm tra mật khẩu có ít nhất một ký tự đặc biệt
        if (!password.matches(".*[@$!%*?&].*")) {
            model.addAttribute("error", "Mật khẩu phải chứa ít nhất một ký tự đặc biệt.");
            return "/User/register";
        }

        // Kiểm tra lỗi validation khác (email, tên, v.v...)
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Nhập dữ liệu không đúng. Bạn hãy nhập lại!!");
            return "/User/register";
        }

        try {
            // Tạo người dùng mới
            User user = userService.createUser(request);
            UserDto userDto = userService.convertUserToDto(user);

            // Thêm thông báo thành công
            model.addAttribute("message", "Tạo tài khoản Gạo Quê thành công.");
            model.addAttribute("user", userDto);

            // Chuyển hướng tới trang login
            return "/User/login";
        } catch (AlreadyExistsException e) {
            // Xử lý khi tài khoản đã tồn tại
            model.addAttribute("error", e.getMessage());
            return "/User/register";
        }
    }
}

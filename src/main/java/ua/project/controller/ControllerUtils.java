package ua.project.controller;

import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import ua.project.entity.enums.CarStatus;
import ua.project.entity.enums.CarType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ControllerUtils {
    public static List<String> getCarTypes(){
        return Stream.of(CarType.values())
                .map(CarType::name)
                .collect(Collectors.toList());
    }
    public static List<String> getCarStatuses(){
        return Stream.of(CarStatus.values())
                .map(CarStatus::name)
                .collect(Collectors.toList());
    }
    public static Sort getSort(String sortBy , String nameBy , Model model){
        Sort sort = null;
        if(nameBy != null && !nameBy.isEmpty()){
            sort = Sort.by(getSortType(sortBy), nameBy);
            model.addAttribute("sort", sortBy);
            model.addAttribute("nameBy", nameBy);
        }
        return sort;
    }
    public static String getSearchField(String username, Model model){
        String searchField = "";
        if(username!=null && !username.isEmpty()){
            searchField = username;
            model.addAttribute("username" , username);
        }
        return  username;
    }
    public static Sort.Direction getSortType(String type){
        return type == null || type.isEmpty() ? Sort.DEFAULT_DIRECTION : Sort.Direction.valueOf(type);
    }
    public static void pageNumberCounts(int totalPages , Model model){
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}

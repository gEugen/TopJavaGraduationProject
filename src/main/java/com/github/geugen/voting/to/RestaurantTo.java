package com.github.geugen.voting.to;

import com.github.geugen.voting.model.Address;
import com.github.geugen.voting.model.MenuItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.List;


@Schema(title = "RestaurantTo - used to restaurant data with menu items getting only")
@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo {

    @NotNull
    Address address;

    List<MenuItem> menuItems;

    public RestaurantTo(Integer id, String name, Address address, List<MenuItem> menuItems) {
        super(id, name);
        this.address = address;
        this.menuItems = menuItems;
    }

    @Override
    public String toString() {
        return "RestaurantTo:" + id + '[' + address.toString() + ']';
    }
}

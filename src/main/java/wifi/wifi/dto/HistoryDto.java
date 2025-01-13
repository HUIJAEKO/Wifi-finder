package wifi.wifi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDto {
    private int id;
    private double lat;
    private double lnt;
    private String searchTime;
}

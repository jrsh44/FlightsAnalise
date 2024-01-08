package com.FlightsAnalise.model.receivedJson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Baglimit{
    @JsonProperty("hand_height")
    protected int handHeight;
    @JsonProperty("hand_length")
    protected int handLength;
    @JsonProperty("hand_weight")
    protected int handWeight;
    @JsonProperty("hand_width")
    protected int handWidth;
    @JsonProperty("hold_dimensions_sum")
    protected int holdDimensionsSum;
    @JsonProperty("hold_height")
    protected int holdHeight;
    @JsonProperty("hold_length")
    protected int holdLength;
    @JsonProperty("hold_weight")
    protected int holdWeight;
    @JsonProperty("hold_width")
    protected int holdWidth;
    @JsonProperty("personal_item_height")
    protected int personalItemHeight;
    @JsonProperty("personal_item_length")
    protected int personalItemLength;
    @JsonProperty("personal_item_weight")
    protected int personalItemWeight;
    @JsonProperty("personal_item_width")
    protected int personalItemWidth;
}

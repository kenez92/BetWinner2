package com.kenez92.betwinner.mapper;

import com.kenez92.betwinner.domain.Coupon;
import com.kenez92.betwinner.domain.CouponDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper
public interface CouponMapper {

    Coupon mapToCoupon(CouponDto couponDto);

    CouponDto mapToCouponDto(Coupon coupon);

    default List<CouponDto> mapToCouponDtoList(List<Coupon> couponList) {
        if (couponList == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(couponList).stream()
                .map(this::mapToCouponDto)
                .collect(Collectors.toList());
    }
}
//public class CouponMapper {
//    public Coupon mapToCoupon(final CouponDto couponDto) {
//        return Coupon.builder()
//                .id(couponDto.getId())
//                .matchList(mapToMatchList(couponDto.getMatchDtoList()))
//                .build();
//    }
//
//    public CouponDto mapToCouponDto(final Coupon coupon) {
//        return CouponDto.builder()
//                .id(coupon.getId())
//                .matchDtoList(mapToMatchDtoList(coupon.getMatchList()))
//                .build();
//    }
//
//    public List<CouponDto> mapToCouponDtoList(final List<Coupon> couponList) {
//        if (couponList == null) {
//            return new ArrayList<>();
//        }
//        return new ArrayList<>(couponList).stream()
//                .map(this::mapToCouponDto)
//                .collect(Collectors.toList());
//    }
//
//    private List<MatchDto> mapToMatchDtoList(List<Match> matchList) {
//        if (matchList == null) {
//            return new ArrayList<>();
//        }
//        return new ArrayList<>(matchList).stream()
//                .map(match -> MatchDto.builder()
//                        .id(match.getId())
//                        .footballId(match.getFootballId())
//                        .homeTeam(match.getHomeTeam())
//                        .awayTeam(match.getAwayTeam())
//                        .competitionId(match.getCompetitionId())
//                        .seasonId(match.getSeasonId())
//                        .date(match.getDate())
//                        .homeTeamPositionInTable(match.getHomeTeamPositionInTable())
//                        .awayTeamPositionInTable(match.getAwayTeamPositionInTable())
//                        .homeTeamChance(match.getHomeTeamChance())
//                        .awayTeamChance(match.getAwayTeamChance())
//                        .round(match.getRound())
//                        .matchDay(null)
//                        .matchScore(null)
//                        .weather(null)
//                        .couponDtoList(new ArrayList<>())
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//
//    private List<Match> mapToMatchList(final List<MatchDto> matchDtoList) {
//        if (matchDtoList == null) {
//            return new ArrayList<>();
//        }
//        return new ArrayList<>(matchDtoList).stream()
//                .map(matchDto -> Match.builder()
//                        .id(matchDto.getId())
//                        .footballId(matchDto.getFootballId())
//                        .homeTeam(matchDto.getHomeTeam())
//                        .awayTeam(matchDto.getAwayTeam())
//                        .competitionId(matchDto.getCompetitionId())
//                        .seasonId(matchDto.getSeasonId())
//                        .date(matchDto.getDate())
//                        .homeTeamPositionInTable(matchDto.getHomeTeamPositionInTable())
//                        .awayTeamPositionInTable(matchDto.getAwayTeamPositionInTable())
//                        .homeTeamChance(matchDto.getHomeTeamChance())
//                        .awayTeamChance(matchDto.getAwayTeamChance())
//                        .round(matchDto.getRound())
//                        .matchDay(null)
//                        .matchScore(null)
//                        .weather(null)
//                        .couponList(new ArrayList<>())
//                        .build())
//                .collect(Collectors.toList());
//    }
//}

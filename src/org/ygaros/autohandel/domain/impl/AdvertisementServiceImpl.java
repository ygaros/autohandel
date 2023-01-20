package org.ygaros.autohandel.domain.impl;

import org.ygaros.autohandel.domain.AdvertisementService;
import org.ygaros.autohandel.model.advertisement.AdvertisementType;
import org.ygaros.autohandel.model.player.Player;
import org.ygaros.autohandel.model.player.TransactionEntry;
import org.ygaros.autohandel.model.player.TransactionType;

public class AdvertisementServiceImpl implements AdvertisementService {

    @Override
    public TransactionEntry buyAd(AdvertisementType type, Player player) {
        if(player.removeFromBalance(type.getCost())){
            return new TransactionEntry(type.getCost(),
                    type.equals(AdvertisementType.INTERNET) ?
                            TransactionType.BUY_INTERNET_ADVERTISEMENT : TransactionType.BUY_PAPER_ADVERTISEMENT
            );
        }
        return new TransactionEntry(type.getCost(), TransactionType.NOT_ENOUGH_FUNDS);

    }
}

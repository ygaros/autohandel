package org.ygaros.autohandel.domain;

import org.ygaros.autohandel.model.advertisement.AdvertisementType;
import org.ygaros.autohandel.model.player.Player;
import org.ygaros.autohandel.model.player.TransactionEntry;

public interface AdvertisementService {

    TransactionEntry buyAd(AdvertisementType type, Player player);
}

/*
 * Solo - A small and beautiful blogging system written in Java.
 * Copyright (c) 2010-2019, b3log.org & hacpai.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.b3log.solo.upgrade;

import org.b3log.latke.Keys;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.Transaction;
import org.b3log.solo.model.Option;
import org.b3log.solo.repository.OptionRepository;
import org.json.JSONObject;

/**
 * Upgrade script from v3.3.0 to v3.4.0.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.0, Mar 29, 2019
 * @since 3.5.0
 */
public final class V340_350 {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(V340_350.class);

    /**
     * Performs upgrade from v3.3.0 to v3.4.0.
     *
     * @throws Exception upgrade fails
     */
    public static void perform() throws Exception {
        final String fromVer = "3.4.0";
        final String toVer = "3.5.0";

        LOGGER.log(Level.INFO, "Upgrading from version [" + fromVer + "] to version [" + toVer + "]....");

        final BeanManager beanManager = BeanManager.getInstance();
        final OptionRepository optionRepository = beanManager.getReference(OptionRepository.class);

        try {
            final Transaction transaction = optionRepository.beginTransaction();

            optionRepository.remove("skinName");
            optionRepository.remove("skins");
            final JSONObject skinOpt = optionRepository.get(Option.ID_C_SKIN_DIR_NAME);
            skinOpt.put(Option.OPTION_CATEGORY, Option.CATEGORY_C_SKIN);
            optionRepository.update(Option.ID_C_SKIN_DIR_NAME, skinOpt);
            final JSONObject mobileSkinOpt = optionRepository.get(Option.ID_C_MOBILE_SKIN_DIR_NAME);
            mobileSkinOpt.put(Option.OPTION_CATEGORY, Option.CATEGORY_C_SKIN);
            optionRepository.update(Option.ID_C_SKIN_DIR_NAME, mobileSkinOpt);

            JSONObject hljsThemeOpt = optionRepository.get(Option.ID_C_HLJS_THEME);
            if (null == hljsThemeOpt) {
                hljsThemeOpt = new JSONObject();
                hljsThemeOpt.put(Keys.OBJECT_ID, Option.ID_C_HLJS_THEME);
                hljsThemeOpt.put(Option.OPTION_CATEGORY, Option.CATEGORY_C_PREFERENCE);
                hljsThemeOpt.put(Option.OPTION_VALUE, Option.DefaultPreference.DEFAULT_HLJS_THEME);
                optionRepository.add(hljsThemeOpt);
            }

            final JSONObject versionOpt = optionRepository.get(Option.ID_C_VERSION);
            versionOpt.put(Option.OPTION_VALUE, toVer);
            optionRepository.update(Option.ID_C_VERSION, versionOpt);

            transaction.commit();

            LOGGER.log(Level.INFO, "Upgraded from version [" + fromVer + "] to version [" + toVer + "] successfully");
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Upgrade failed!", e);

            throw new Exception("Upgrade failed from version [" + fromVer + "] to version [" + toVer + "]");
        }
    }
}
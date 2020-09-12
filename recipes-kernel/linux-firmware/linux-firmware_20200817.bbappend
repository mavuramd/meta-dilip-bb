# Include RTL8192e* Firmware
PACKAGES =+ " ${PN}-rtl8192e"

# For rtl
LICENSE_${PN}-rtl8192e = "Firmware-rtlwifi_firmware"

FILES_${PN}-rtl8192e = " \
  ${nonarch_base_libdir}/firmware/rtlwifi/rtl8192e*.bin \
"

RDEPENDS_${PN}-rtl8192e += "${PN}-rtl-license"

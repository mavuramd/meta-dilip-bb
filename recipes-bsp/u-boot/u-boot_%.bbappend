FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://boot-command-boot-4m-mmc0.patch"

UBOOT_SUFFIX = "img"
SPL_BINARY = "MLO"
